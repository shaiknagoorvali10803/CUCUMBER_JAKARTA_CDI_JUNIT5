package com.csx;

import io.cucumber.core.backend.ObjectFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Vetoed;
import jakarta.inject.Inject;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CdiJakartaFactoryTest {

    final ObjectFactory factory = new CdiJakartaFactory();

    @AfterEach
    void stop() {
        factory.stop();
        IgnoreLocalBeansXmlClassLoader.restoreClassLoader();
    }

    @Test
    void lifecycleIsIdempotent() {
        Assertions.assertDoesNotThrow(factory::stop);
        factory.start();
        Assertions.assertDoesNotThrow(factory::start);
        factory.stop();
        Assertions.assertDoesNotThrow(factory::stop);
    }

    @Vetoed
    static class VetoedBean {

    }

    @ParameterizedTest
    @ValueSource(booleans = { true, false })
    void shouldCreateNewInstancesForEachScenario(boolean ignoreLocalBeansXml) {
        IgnoreLocalBeansXmlClassLoader.setClassLoader(ignoreLocalBeansXml);
        // Scenario 1
        factory.start();
        factory.addClass(VetoedBean.class);
        VetoedBean a1 = factory.getInstance(VetoedBean.class);
        VetoedBean a2 = factory.getInstance(VetoedBean.class);
        MatcherAssert.assertThat(a1, Is.is(IsEqual.equalTo(a2)));
        factory.stop();

        // Scenario 2
        factory.start();
        VetoedBean b1 = factory.getInstance(VetoedBean.class);
        factory.stop();

        // VetoedBean makes it possible to compare the object outside the
        // scenario/application scope
        Assertions.assertAll(
            () -> MatcherAssert.assertThat(a1, Is.is(IsNull.notNullValue())),
            () -> MatcherAssert.assertThat(a1, Is.is(IsNot.not(IsEqual.equalTo(b1)))),
            () -> MatcherAssert.assertThat(b1, Is.is(IsNot.not(IsEqual.equalTo(a1)))));
    }

    @ApplicationScoped
    static class ApplicationScopedBean {

    }

    @ParameterizedTest
    @ValueSource(booleans = { true, false })
    void shouldCreateApplicationScopedInstance(boolean ignoreLocalBeansXml) {
        IgnoreLocalBeansXmlClassLoader.setClassLoader(ignoreLocalBeansXml);
        factory.addClass(ApplicationScopedBean.class);
        factory.start();
        ApplicationScopedBean bean = factory.getInstance(ApplicationScopedBean.class);
        Assertions.assertAll(
            // assert that it is is a CDI proxy
            () -> MatcherAssert.assertThat(bean.getClass(), IsNot.not(Is.is(ApplicationScopedBean.class))),
            () -> MatcherAssert.assertThat(bean.getClass().getSuperclass(), Is.is(ApplicationScopedBean.class)));
        factory.stop();
    }

    static class UnmanagedBean {

    }

    @ParameterizedTest
    @ValueSource(booleans = { true, false })
    void shouldCreateUnmanagedInstance(boolean ignoreLocalBeansXml) {
        IgnoreLocalBeansXmlClassLoader.setClassLoader(ignoreLocalBeansXml);
        factory.start();
        UnmanagedBean bean = factory.getInstance(UnmanagedBean.class);
        MatcherAssert.assertThat(bean.getClass(), Is.is(UnmanagedBean.class));
        factory.stop();
    }

    static class OtherStepDefinitions {

    }

    static class StepDefinitions {

        @Inject
        OtherStepDefinitions injected;

    }

    @ParameterizedTest
    @ValueSource(booleans = { true, false })
    void shouldInjectStepDefinitions(boolean ignoreLocalBeansXml) {
        IgnoreLocalBeansXmlClassLoader.setClassLoader(ignoreLocalBeansXml);
        factory.addClass(OtherStepDefinitions.class);
        factory.addClass(StepDefinitions.class);
        factory.start();
        StepDefinitions stepDefinitions = factory.getInstance(StepDefinitions.class);
        MatcherAssert.assertThat(stepDefinitions.injected, Is.is(IsNull.notNullValue()));
        factory.stop();
    }

}
