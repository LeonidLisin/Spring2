package ru.geekbrains.supershop.Utils;

import java.util.UUID;

public class Validator {

        public static boolean isUUIDvalid(String string) {
            try {
                UUID.fromString(string);
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
}
