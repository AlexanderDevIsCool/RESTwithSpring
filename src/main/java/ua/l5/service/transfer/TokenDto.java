package ua.l5.service.transfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import ua.l5.service.models.Token;

@Data
@AllArgsConstructor
public class TokenDto {
    private String value;

    public static TokenDto from(Token token){
        return new TokenDto(token.getValue());
    }
}
