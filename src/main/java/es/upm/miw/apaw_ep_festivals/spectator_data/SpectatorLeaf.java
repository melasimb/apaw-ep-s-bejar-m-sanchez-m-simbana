package es.upm.miw.apaw_ep_festivals.spectator_data;

public class SpectatorLeaf implements SpectatorComponent {

    Spectator spectator;

    public SpectatorLeaf(Spectator spectator) {
        this.spectator = spectator;
    }

    @Override
    public void add(SpectatorComponent spectatorComponent) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public void remove(SpectatorComponent spectatorComponent) {
        //Do nothing because is a leaf
    }

    @Override
    public String info() {
        return this.spectator.getName() + " " + this.spectator.getSurname();
    }
}
