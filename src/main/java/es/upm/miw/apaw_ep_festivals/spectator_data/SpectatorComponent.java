package es.upm.miw.apaw_ep_festivals.spectator_data;


public interface SpectatorComponent {
    void add(SpectatorComponent spectatorComponent);

    void remove(SpectatorComponent spectatorComponent);

    String info();
}
