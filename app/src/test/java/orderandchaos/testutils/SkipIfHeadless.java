package orderandchaos.testutils;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.awt.*;

public class SkipIfHeadless implements ExecutionCondition {

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        if (GraphicsEnvironment.isHeadless()) {
            return ConditionEvaluationResult.disabled("Headless environment detected. Test skipped.");
        }
        return ConditionEvaluationResult.enabled("Graphical environment detected.");
    }
}
