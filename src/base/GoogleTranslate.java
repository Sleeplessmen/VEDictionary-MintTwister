public class GoogleTranslate {

    private static final Translate translate = TranslateOptions.getDefaultInstance().getService();

    public static String translateText(String text, String targetLanguage) {
        Translation translation = translate.translate(text, Translate.TranslateOption.targetLanguage(targetLanguage));
        return translation.getTranslatedText();
    }
}