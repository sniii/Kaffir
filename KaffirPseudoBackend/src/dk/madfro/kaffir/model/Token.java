package dk.madfro.kaffir.model;

import java.util.UUID;

public class Token {
	private String secretKey;
	
	private Token() {
		secretKey = UUID.randomUUID().toString();
	}
	
	private Token(String token) {
		secretKey = token;
	}
	
	public static Token create() {
		return new Token();
	}
	
	public static Token createFrom(String token) {
		return new Token(token);
	}
	
	public String getSecretKey() {
		return secretKey;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((secretKey == null) ? 0 : secretKey.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Token other = (Token) obj;
		if (secretKey == null) {
			if (other.secretKey != null)
				return false;
		} else if (!secretKey.equals(other.secretKey))
			return false;
		return true;
	}
}