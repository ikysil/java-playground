package io.github.ikysil.javaplayground.narrow;

import java.util.List;
import java.util.Optional;

public class TypeNarrowDemo {

    static final class Types {

        private Types() throws IllegalAccessException {
            throw new IllegalAccessException();
        }

        static <T> T narrowToNull(final Object obj, final Class<T> expectedType) {
            if (expectedType.isInstance(obj)) {
                return expectedType.cast(obj);
            }
            return null;
        }

        static <T> Optional<T> narrow(final Object obj, final Class<T> expectedType) {
            return Optional.ofNullable(obj)
                .map(o -> narrowToNull(o, expectedType));
        }

    }

    public static void main(String[] args) {
        System.out.println("TypeNarrowDemo Demo");
        if (Types.narrowToNull("test", Object.class) != null) {
            System.out.println("SUCCESS: String is Object");
        }
        Types.narrow("test", Object.class)
            .ifPresent(o -> System.out.println("SUCCESS: String is Object: " + o));
        if (Types.narrowToNull("test", List.class) == null) {
            System.out.println("SUCCESS: String is NOT List");
        }
        Types.narrow("test", List.class)
            .ifPresentOrElse(
                o -> System.out.println("ERROR: String is NOT List: " + o),
                () -> System.out.println("SUCCESS: String is NOT List")
            );
    }

}
