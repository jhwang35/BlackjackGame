import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MyTest extends JavaFXTemplate {

	@Test
	void test() {
		animals dog1 = new dog();
		animals dog2 = new cow();
		assertEquals(dog1.noise(), dog2.noise());

	}

}
