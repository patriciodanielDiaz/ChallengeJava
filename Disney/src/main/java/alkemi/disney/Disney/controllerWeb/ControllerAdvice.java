package alkemi.disney.Disney.controllerWeb;

import alkemi.disney.Disney.dto.ErrorDto;
import alkemi.disney.Disney.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler( CharacterNotUpdated.class)
    public ErrorDto handleRecordNotExistsException(HttpServletRequest request, Exception exc) {
        return new ErrorDto(0,exc,request.getRequestURI());
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({UncreatedCharacter.class,UncreatedMovie.class,CharacterNotFound.class, MovieNotFound.class,RecordNotExistsException.class})
    public ErrorDto handleUncreatedCharacter(HttpServletRequest request, Exception exc) {
        return new ErrorDto(1,exc,request.getRequestURI());
    }


}
