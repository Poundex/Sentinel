package net.poundex.sentinel.caretaker.environment

class PersistentRoom implements Room
{
	String name

    static constraints = {
    }

	boolean equals(o)
	{
		if (this.is(o)) return true
		if (!(o instanceof PersistentRoom)) return false

		PersistentRoom that = (PersistentRoom) o

		if(id == null || that.id == null) return false
		if (id != that.id) return false

		return true
	}

	int hashCode()
	{
		return (id != null ? id.hashCode() : 0)
	}
}
