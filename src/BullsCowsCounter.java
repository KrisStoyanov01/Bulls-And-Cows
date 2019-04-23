public class BullsCowsCounter {
    public int bullCount = 0;
    public int cowCount = 0;

    public BullsCowsCounter(int b, int c) {
        this.bullCount = b;
        this.cowCount = c;
    }

    public int getBullCount() {
        return this.bullCount;
    }

    public int getCowCount() {
        return this.cowCount;
    }

    public String toString() {
        return this.bullCount + " " + this.cowCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BullsCowsCounter other = (BullsCowsCounter) obj;
        if (this.bullCount != other.bullCount || this.cowCount != other.cowCount) {
            return false;
        }
        return true;
    }
}
