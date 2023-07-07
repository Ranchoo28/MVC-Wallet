package it.unical.demacs.informatica.mvcwallet.handler;

public class RegexHandler {

    private static final RegexHandler instance = new RegexHandler();
    public static RegexHandler getInstance() {
        return instance;
    }
    private RegexHandler() {}

    public final String regexEmail =
            "^[a-zA-Z0-9.!#$%&’*+/=?^_{|}~-]+@(?:gmail\\.com|yahoo\\.com|hotmail\\.com|libero\\.it|icloud\\.com|gmx\\.com|aol\\.com)";

    public final String regexPassword=
            "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@!%&£°#'?*=])[a-zA-Z0-9@!%&£°#'?*=]{8,}";
}
