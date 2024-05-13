package academy.carX.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RecourseNotFoundException extends RuntimeException{
    public RecourseNotFoundException(String massage) {
        super(massage);
    }
}
