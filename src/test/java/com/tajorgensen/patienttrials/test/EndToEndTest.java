package com.tajorgensen.patienttrials.test;


import org.junit.jupiter.api.Tag;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The purpose of this annotation is to simplify the end-to-end test process.  All tests can remain under the src/test folder, but we use tags
 * effectively to identify types of tests.  If you want to run al the end-to-end tests you can simply run: <pre><code>mvn test -Dgroups="endToEnd"</code></pre>
 * and it will grab all tests with this tag
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Tag("endToEnd")
@ActiveProfiles("junit-end-to-end")
public @interface EndToEndTest {
}
