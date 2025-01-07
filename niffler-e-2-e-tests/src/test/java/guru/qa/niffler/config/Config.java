package guru.qa.niffler.config;

public interface Config {

  static Config getInstance() {
    return LocalConfig.INSTANCE;
  }

  String frontUrl();

  String authUrl();

  String gateWayUrl();

  String userDataUrl();

  String spendUrl();

  String ghUrl();
}
