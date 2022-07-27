package Objects;

import Login.Hasher;

public class Handle {
    private String handle;

    public Handle(String id){handle = id;}

    public String getHandle() {return handle;}

    public void setHandle(String handle) {this.handle = handle;}
    public void hashAndSetHandle(String toHash){handle = Hasher.hash(toHash);}

    @Override
    public boolean equals(Object o){
        if (!o.getClass().equals(Handle.class)) return false;
        return o.toString().equals(handle);
    }

    @Override
    public String toString(){return handle;}
}
