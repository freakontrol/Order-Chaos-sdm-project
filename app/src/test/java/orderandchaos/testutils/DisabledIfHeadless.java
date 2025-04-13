package orderandchaos.testutils;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(SkipIfHeadless.class)
public @interface DisabledIfHeadless {
}
