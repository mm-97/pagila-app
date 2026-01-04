package org.mm97.pagilab.dto;

public record FilmActorDto(
        int actorId,
        String actorFirstName,
        String actorLastName,
        int filmId,
        String filmTitle
) {
}