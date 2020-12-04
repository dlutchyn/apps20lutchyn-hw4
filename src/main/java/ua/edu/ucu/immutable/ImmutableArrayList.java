package ua.edu.ucu.collections.immutable;

import java.util.Arrays;

public class ImmutableArrayList {
    public int length;
    public final Object[] arrayList;

    public ImmutableArrayList(Object[] startList) {
        this.arrayList = new Object[startList.length];
        this.length = startList.length;
        for (int index = 0; index < startList.length; index++) {
            this.arrayList[index] = startList[index];
        }
    }

    private void checkIndex(int index)
            throws StringIndexOutOfBoundsException {
        if (index < 0 || index > this.length) {
            throw new StringIndexOutOfBoundsException("Incorrect index value");
        }
    }

    public ImmutableArrayList add(Object e) {
        Object[] oneElemArr = {e};
        return this.addAll(this.length, oneElemArr);
    }

    public ImmutableArrayList add(int index, Object e)
            throws StringIndexOutOfBoundsException {
        this.checkIndex(index);
        Object[] oneElemArr = {e};
        return this.addAll(index, oneElemArr);
    }

    public ImmutableArrayList addAll(Object[] c) {
        return this.addAll(this.length, c);
    }

    public ImmutableArrayList addAll(int index, Object[] c)
            throws StringIndexOutOfBoundsException {
        this.checkIndex(index);
        ImmutableArrayList copyArray = new
                ImmutableArrayList(new Object[this.length + c.length]);

        if (index == this.length) {
            int helpIndex = 0;
            while (helpIndex < c.length) {
                copyArray.arrayList[this.length + helpIndex] = c[helpIndex];
                helpIndex++;
            }
        }

        for (int i = 0; i < this.length; i++) {
            if (i < index) {
                copyArray.arrayList[i] = this.arrayList[i];
            } else if (i == index) {
                int helpIndex = 0;
                while (helpIndex < c.length) {
                    copyArray.arrayList[i + helpIndex] = c[helpIndex];
                    helpIndex++;
                }
                copyArray.arrayList[i + helpIndex] = this.arrayList[i];
            } else {
                copyArray.arrayList[i + c.length] = this.arrayList[i];
            }
        }
        return copyArray;
    }

    public Object get(int index) throws StringIndexOutOfBoundsException {
        this.checkIndex(index);
        return this.arrayList[index];
    }

    public ImmutableArrayList remove(int index)
            throws StringIndexOutOfBoundsException {
        this.checkIndex(index);
        ImmutableArrayList copyArray = new
                ImmutableArrayList(new Object[this.length - 1]);
        for (int i = 0; i < this.length; i++) {
            if (i < index) {
                copyArray.arrayList[i] = this.arrayList[i];
            } else if (i > index) {
                copyArray.arrayList[i - 1] = this.arrayList[i];
            }
        }
        return copyArray;
    }

    public ImmutableArrayList set(int index, Object e)
            throws StringIndexOutOfBoundsException {
        this.checkIndex(index);
        ImmutableArrayList copyArray = new ImmutableArrayList(this.toArray());
        copyArray.arrayList[index] = e;
        return copyArray;
    }

    public int indexOf(Object e) {
        for (int i = 0; i < this.length; i++) {
            if (this.arrayList[i] == e) {
                return i;
            }
        }
        return -1;
    }

    public int size() {
        return this.length;
    }

    public ImmutableArrayList clear() {
        ImmutableArrayList copyArray = new ImmutableArrayList(new Object[0]);
        return copyArray;
    }

    public boolean isEmpty() {
        return this.length == 0;
    }

    public Object[] toArray() {
        Object[] copyArray = new Object[this.length];
        for (int i = 0; i < this.length; i++) {
            copyArray[i] = this.arrayList[i];
        }
        return copyArray;
    }

    @Override
    public String toString() {
        return Arrays.toString(this.arrayList);
    }
    
}
