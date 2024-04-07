package org.recordreplay;

import io.opentelemetry.context.Scope;
import io.opentelemetry.javaagent.extension.instrumentation.TypeInstrumentation;
import io.opentelemetry.javaagent.extension.instrumentation.TypeTransformer;
import jakarta.servlet.http.HttpServletRequest;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import org.springframework.http.ResponseEntity;

import java.io.*;
import java.util.HashMap;
import java.util.logging.Logger;

import static net.bytebuddy.matcher.ElementMatchers.namedOneOf;

public class RecordInstrumention implements TypeInstrumentation {
    public static Logger logger = Logger.getLogger(RecordInstrumention.class.getName());

    @Override
    public ElementMatcher<TypeDescription> typeMatcher() {
        return ElementMatchers.named("com.nfr.record.controller.PostController");
    }

    @Override
    public void transform(TypeTransformer typeTransformer) {
        typeTransformer.applyAdviceToMethod(namedOneOf("createNewPost"),this.getClass().getName() + "$CreatePostAdvice");
    }

    @SuppressWarnings("unused")
    public static class CreatePostAdvice {

        @Advice.OnMethodEnter(suppress = Throwable.class)
        public static Scope onEnter() {
            logger.info("Entry" + "OnEnter Called");
            return null;
        }

        @Advice.OnMethodExit(onThrowable = Throwable.class, suppress = Throwable.class)
        public static void onExit(@Advice.Return(readOnly = false) ResponseEntity httpResponse,
                                  @Advice.Argument(value = 0) HttpServletRequest input,
                                  @Advice.Thrown Throwable throwable,
                                  @Advice.Enter Scope scope) throws IOException {
            if (throwable != null) {
                logger.info("recorded exception");
            } else {
                logger.info("recorded success");
            }

            HashMap<String, Object> map = new HashMap();
            map.put("response", httpResponse);
            map.put("request", input);
            map.put("url", input.getRequestURL());
            map.put("method", input.getMethod());
            // save the data in json file for saving recorded data
            logger.info("MAPDATA" + map);
        }
    }
}