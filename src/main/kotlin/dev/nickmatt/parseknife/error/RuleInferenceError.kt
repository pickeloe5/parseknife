package dev.nickmatt.parseknife.error


class RuleInferenceError(
    val received: Any
): Error(makeMessage(received)) {
    companion object {

        private fun makeMessage(received: Any) =
            "Expected rule literal (char, string, regex, etc.), received: ${received.javaClass.name}"

    }
}