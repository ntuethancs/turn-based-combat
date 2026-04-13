package entity.item;

import entity.action.ActionContext;
import entity.interfaces.Describable;
import entity.interfaces.Named;

public abstract class Item implements Named, Describable {
    protected boolean used = false;

    public boolean isUsed() { return used; }

    public abstract Item copy();

    public abstract void use(ActionContext ctx);
}