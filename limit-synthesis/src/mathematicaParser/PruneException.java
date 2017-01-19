package mathematicaParser;

//thrown when a case not desired in the gene pool is found.
public class PruneException extends Exception {
	public PruneException(String message)
	{
		super(message);
	}
}
