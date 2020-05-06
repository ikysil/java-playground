package io.github.ikysil.javaplayground.typeswitch;

import javax.swing.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TypeSwitchDemo {

    static class TypeSwitch<T> {

        private final T instance;

        private TypeSwitch(T instance) {
            this.instance = instance;
        }

        static <T> TypeSwitch<T> of(final T instance) {
            return new TypeSwitch<>(instance);
        }

        static <T> TypeSwitch<T> of(final Supplier<T> instanceSupplier) {
            return new TypeSwitch<>(instanceSupplier.get());
        }

        <I> TypeSwitch<T> instanceOf(final Class<I> type, final Consumer<I> consumer) {
            if (type.isInstance(instance)) {
                consumer.accept(type.cast(instance));
            }
            return this;
        }

        void noneMatches(final Consumer<Object> consumer) {
            consumer.accept(instance);
        }

    }

    public static void main(String[] args) {
        System.out.println("TypeSwitch Demo");
        TypeSwitch.of(new JLabel())
            .instanceOf(JComponent.class, o -> System.out.println("matched JComponent"));
        TypeSwitch.of(new Object())
            .instanceOf(JComponent.class, o -> System.out.println("matched JComponent"))
            .noneMatches(o -> System.out.printf("none matched: %s of %s%n", o, o.getClass()));
    }

}
