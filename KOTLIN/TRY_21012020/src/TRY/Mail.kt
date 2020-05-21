package TRY

class Mail {
    fun sendMessageToClient(
        client: Client?,
        message: String?,
        mailer: Mailer
    ) {
        if (client == null || message == null) return
        val personalInfo = client.personalInfo ?: return
        val email = personalInfo.email ?: return
        mailer.sendMessage(email, message)
    }
}