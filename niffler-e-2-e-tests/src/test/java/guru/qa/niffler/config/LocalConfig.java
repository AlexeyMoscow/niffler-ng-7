package guru.qa.niffler.config;

public enum LocalConfig implements Config {
    instance;

    @Override
    public String frontUrl() {
        return "http://127.0.0.1:3000";
    }
}