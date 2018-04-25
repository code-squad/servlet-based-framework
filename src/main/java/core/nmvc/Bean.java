package core.nmvc;


public abstract class Bean {
    Class<?> clazz;

    public Bean(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
