import com.aallam.openai.api.completion.Choice
import com.aallam.openai.api.completion.CompletionRequest
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import kotlin.system.exitProcess

const val OPEN_AI_TOKEN = "<enter-secret>"
const val OPEN_AI_MODEL = "ada"

suspend fun main(args: Array<String>) {
    openAIRequestFor("Someone told me that the secret of a happy life is")
        .onSuccess { it.forEach { choice -> println("### ${choice.text} ###") } }
        .onFailure { it.printStackTrace() }
        .let { exitProcess(0) }
}

private suspend fun openAIRequestFor(prompt: String): Result<List<Choice>> {
    val openAI = OpenAI(OPEN_AI_TOKEN)
    val modelId = ModelId(OPEN_AI_MODEL)
    return runCatching {
        val completion = openAI.completion(
            request = CompletionRequest(
                model = modelId,
                prompt = prompt,
                maxTokens = 25,
                n = 2,
                echo = true
            )
        )
        completion.choices
    }
}