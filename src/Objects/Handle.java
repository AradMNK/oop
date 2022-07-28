package Objects;

public class Handle {
    private int handle;

    public Handle(int id){handle = id;}

    public int getHandle() {return handle;}

    public void setHandle(int handle) {this.handle = handle;}

    @Override
    public boolean equals(Object o){
        if (!o.getClass().equals(Handle.class)) return false;
        return (((Handle) o).handle == handle);
    }
}
