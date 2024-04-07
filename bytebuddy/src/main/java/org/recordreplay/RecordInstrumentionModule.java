package org.recordreplay;

import com.google.auto.service.AutoService;
import io.opentelemetry.javaagent.extension.instrumentation.InstrumentationModule;
import io.opentelemetry.javaagent.extension.instrumentation.TypeInstrumentation;
import io.opentelemetry.javaagent.extension.matcher.AgentElementMatchers;
import net.bytebuddy.matcher.ElementMatcher;

import java.util.Collections;
import java.util.List;

@AutoService(InstrumentationModule.class)
public final class RecordInstrumentionModule extends InstrumentationModule {
    public RecordInstrumentionModule() {
        super("record-replay", "record");
    }

    @Override
    public int order() {
        return 1;
    }
    @Override
    public List<String> getAdditionalHelperClassNames() {
        return List.of(RecordInstrumention.class.getName(), "io.opentelemetry.javaagent.extension.instrumentation.TypeInstrumentation",
                "jakarta.servlet.http.HttpServletRequest");
    }

    @Override
    public ElementMatcher.Junction<ClassLoader> classLoaderMatcher() {
        return AgentElementMatchers.hasClassesNamed("com.nfr.record.controller.PostController");
    }

    @Override
    public List<TypeInstrumentation> typeInstrumentations() {
        return Collections.singletonList(new RecordInstrumention());
    }
}