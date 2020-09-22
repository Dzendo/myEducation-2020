package com.example.android.advancedcoroutines.util

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Cache the first non-error result from an async computation passed as [block].
 * Кэшируйте первый результат без ошибок асинхронного вычисления, переданного как [block].
 *
 * Usage:
 *
 * ```
 * val cachedSuccess: CacheOnSuccess<Int> = CacheOnSuccess(onErrorFallback = { 3 }) {
 *     delay(1_000) // compute value using coroutines
 *     5
 * }
 *
 * cachedSuccess.getOrAwait() // get the result from the cache, calling [block], or fallback on
 *                            // exception
 * ```
 *
 * @param onErrorFallback: Invoke this if [block] throws exception other than cancellation, the
 *        result of this lambda will be returned for this call to [getOrAwait] but will not be
 *        cached for future calls to [getOrAwait]
 * @param onError Fallback: вызовите это, если [block] вызывает исключение, отличное от отмены, то
 * результат этой лямбды будет возвращен для этого вызова в [get или Await], но не будет возвращен.
 * кэшируется для будущих вызовов в [get или Await]
 *
 * @param block Suspending lambda that produces the cached value. The first non-exceptional value
 *        returned by [block] will be cached, and future calls to [getOrAwait] will return the
 *        cached value or throw a [CancellationException].
 * @param блокирует приостановку лямбды, которая создает кэшированное значение. Первая не исключительная ценность
 * возвращенные [block] будут кэшироваться, а будущие вызовы [get или Await] будут возвращать
 * кэшированное значение или выбросить [CancellationException].
 */
class CacheOnSuccess<T: Any>(
    private val onErrorFallback: (suspend () -> T)? = null,
    private val block: suspend () -> T
) {
    private val mutex =  Mutex()

    @Volatile
    private var deferred: Deferred<T>? = null

    /**
     * Get the current cached value, or await the completion of [block].
     * Получите текущее кэшированное значение или дождитесь завершения работы [block].
     *
     * The result of [block] will be cached after the fist successful result, and future calls to
     * [getOrAwait] will return the cached value.
     * Результат [block] будет кэшироваться после первого успешного результата, а будущие вызовы
     * [get или Await] вернет кэшированное значение.
     *
     * If multiple coroutines call [getOrAwait] before [block] returns, then [block] will only
     * execute one time. If successful, they will all get the same success result. In the case of
     * error it will not cache, and a later call to [getOrAwait] will retry the [block].
     * Если несколько сопрограмм звонок [получить или ждать] перед [заблокировать] возвращает [заблокировать] только
     * выполнить один раз. В случае успеха все они получат одинаковый результат успеха. В случае с
     * ошибка он не будет кэшироваться, и более поздний вызов [get или Await] повторит попытку [block].
     *
     *
     * If [onErrorFallback] is not null, this function will *always* call the lambda in case of
     * error and will never cache the error result.
     * Если значение [onerror Fallback] не равно null, то эта функция будет *всегда * вызывать лямбду в случае
     * ошибка и никогда не будет кэшировать результат ошибки.
     *
     * @throws Throwable the exception thrown by [block] if [onErrorFallback] is not provided.
     * @throws CancellationException will throw a [CancellationException] if called in a cancelled
     *          coroutine context. This will happen even when reading the cached value.
     *
     * @throws Throwable исключение, создаваемое [block], Если [onerror Fallback] не предусмотрен.
     * @throws CancellationException вызовет исключение [CancellationException] , если оно будет вызвано в cancelled
     * контекст сопрограммы. Это произойдет даже при чтении кэшированного значения.
     */
    suspend fun getOrAwait(): T {
        return supervisorScope {
            // This function is thread-safe _iff_ deferred is @Volatile and all reads and writes hold the mutex.
            // Эта функция потокобезопасна _iff_ deferred is @Volatile, и все операции чтения и записи содержат мьютекс.

            // only allow one coroutine to try running block at a time by using a coroutine-base Mutex
            // разрешить только одной сопрограмме пытаться запускать блок за раз с помощью базового мьютекса сопрограммы
            val currentDeferred = mutex.withLock {
                deferred?.let { return@withLock it }

                async {
                    // Note: mutex is not held in this async block
                    block()
                }.also {
                    // Note: mutex is held here
                    deferred = it
                }
            }

            // await the result, with our custom error handling
            // ожидание результата с помощью нашей пользовательской обработки ошибок
            currentDeferred.safeAwait()
        }
    }

    /**
     * Await for a deferred, with our custom error logic.
     * Ждите отложенного, с нашей пользовательской логикой ошибок.
     *
     * If there is an exception, clear the `deferred` field if this is still the current stored value.
     * Если есть исключение, очистите поле "отложено", если это все еще текущее сохраненное значение.
     *
     * If the exception is cancellation, rethrow it without any changes.
     * Если исключение-отмена, перестройте его без каких-либо изменений.
     *
     * Otherwise, try to get a fallback value from onErrorFallback.
     * В противном случае, попробуйте сделать резервное значение от число резервных.
     *
     * This function is thread-safe _iff_ [deferred] is @Volatile and all reads and writes hold the mutex.
     * Эта функция потокобезопасна _iff_ [deferred] is @Volatile, и все операции чтения и записи содержат мьютекс.
     *
     * @param this the deferred to wait for.
     * @param это отложенное ожидание.
     */
    private suspend fun Deferred<T>.safeAwait(): T {
        try {
            // Note: this call to await will always throw if this coroutine is cancelled
            // Примечание: этот вызов await всегда будет вызван, если эта сопрограмма будет отменена
            return await()
        } catch (throwable: Throwable) {
            // If deferred still points to `this` instance of Deferred, clear it because we don't want to cache errors
            // Если deferred все еще указывает на "этот" экземпляр Deferred,
            // очистите его, потому что мы не хотим кэшировать ошибки
            mutex.withLock {
                if (deferred == this) {
                    deferred = null
                }
            }

            // never consume cancellation
            // никогда не потребляйте отмену заказа
            if (throwable is CancellationException) {
                throw throwable
            }

            // return fallback if provided
            // возврат резервный вариант, если он предусмотрен
            onErrorFallback?.let { fallback -> return fallback() }

            // if we get here the error fallback didn't provide a fallback result,
            // so throw the exception to the caller
            // если мы попадем сюда, то ошибка fallback не обеспечила резервного результата,
            // так что бросьте исключение вызывающему абоненту
            throw throwable
        }
    }

}