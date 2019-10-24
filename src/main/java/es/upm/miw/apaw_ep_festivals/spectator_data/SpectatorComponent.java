package es.upm.miw.apaw_ep_festivals.spectator_data;


public abstract class SpectatorComponent {
    public abstract void add(SpectatorComponent spectatorComponent);

    public abstract void remove(SpectatorComponent spectatorComponent);

    public abstract String info();
}
