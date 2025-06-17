package com.zerobase.gamesell.order.application;

import static com.zerobase.gamesell.order.exception.ErrorCode.AGE_RESTRICTION_VIOLATION;
import static com.zerobase.gamesell.order.exception.ErrorCode.NOT_FOUND_GAME;

import com.user.gamedomain.domain.dto.UserDto;
import com.zerobase.gamesell.order.client.UserClient;
import com.zerobase.gamesell.order.domain.dto.CartResponse;
import com.zerobase.gamesell.order.domain.game.AddGameCartForm;
import com.zerobase.gamesell.order.domain.model.Game;
import com.zerobase.gamesell.order.domain.redis.Cart;
import com.zerobase.gamesell.order.domain.repository.GameRepository;
import com.zerobase.gamesell.order.exception.CustomException;
import com.zerobase.gamesell.order.service.CartService;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartApplication {

  private final CartService cartService;
  private final GameRepository gameRepository;
  private final UserClient userClient;

  public Cart addCart(Long userId, AddGameCartForm form, String token) {

    Game game = gameRepository.findById(form.getId())
        .orElseThrow(() -> new CustomException(NOT_FOUND_GAME));

    UserDto userDto = userClient.getUser(userId, token);

    if (Period.between(userDto.getBirthDate(), LocalDate.now()).getYears()
        < game.getContentRating()) {
      throw new CustomException(AGE_RESTRICTION_VIOLATION);
    }

    AddGameCartForm validatedForm = AddGameCartForm.builder()
        .id(game.getId())
        .title(game.getTitle())
        .price(game.getPrice())
        .contentRating(game.getContentRating())
        .build();

    return cartService.addCart(userId, validatedForm);
  }

  public Cart removeGameCart(Long userId, Long gameId) {
    Cart cart = cartService.getCart(userId);

    if (cart.getGames().removeIf(g -> g.getId().equals(gameId))) {
      cartService.putCart(userId, cart);
    }
    return cart;
  }

  public CartResponse getCart(Long userId, String token) {
    Cart cart = cartService.getCart(userId);
    CartResponse response = refreshCart(cart, token);
    cartService.putCart(userId, response.getCart());
    return response;
  }


  private CartResponse refreshCart(Cart cart, String token) {

    Map<Long, Game> gameMap = gameRepository.findAllById(cart.getGames()
            .stream().map(Cart.Game::getId).collect(Collectors.toList()))
        .stream().collect(Collectors.toMap(Game::getId, game -> game));

    CartResponse response = new CartResponse(cart, new ArrayList<>());
    List<String> messages = new ArrayList<>();

    UserDto userDto = userClient.getUser(cart.getUserId(), token);
    int userAge = Period.between(userDto.getBirthDate(), LocalDate.now()).getYears();

    for (int i = 0; i < cart.getGames().size(); i++) {
      Cart.Game cartGame = cart.getGames().get(i);
      Game g = gameMap.get(cartGame.getId());
      List<String> tmpMessages = new ArrayList<>();

      if (g == null) {
        cart.getGames().remove(cartGame);
        i--;
        tmpMessages.add(cartGame.getTitle() + "게임이 삭제되었습니다.");
        continue;
      }
      if (userAge < g.getContentRating()) {
        cart.getGames().remove(cartGame);
        i--;
        tmpMessages.add(String.format("%s 게임은 연령 제한(%d세 이상)으로 인해 장바구니에서 제거되었습니다.",
            g.getTitle(), g.getContentRating()));
        continue;
      }

      boolean isPriceChanged = false;
      boolean isRatingChanged = false;
      int oldPrice = cartGame.getPrice();
      int oldRating = cartGame.getContentRating();

      if (!g.getPrice().equals(cartGame.getPrice())) {
        isPriceChanged = true;
        cartGame.setPrice(g.getPrice());
      }
      if (!g.getContentRating().equals(cartGame.getContentRating())) {
        isRatingChanged = true;
        cartGame.setContentRating(g.getContentRating());
      }
      if (isPriceChanged && isRatingChanged) {
        log.info("{}의 연령 제한이 {} → {}, 가격이 {} → {}으로 변경되었습니다."
            , g.getTitle(), oldRating, g.getContentRating(), oldPrice, g.getPrice());
        tmpMessages.add(String.format("%s의 연령 제한이 %s → %s, 가격이 %s → %s으로 변경되었습니다.",
            g.getTitle(), oldRating, g.getContentRating(), oldPrice, g.getPrice()));
      } else if (isPriceChanged) {
        log.info("{}의 가격이 {}에서 {}으로 변경되었습니다."
            , cartGame.getTitle(), oldPrice, g.getPrice());
        tmpMessages.add(String.format("%s의 가격이 %s에서 %s으로 변경되었습니다.",
            cartGame.getTitle(), oldPrice, g.getPrice()));
      } else if (isRatingChanged) {
        log.info("{}의 연령 제한이 {}에서 {}으로 변경되었습니다."
            , g.getTitle(), oldRating, g.getContentRating());
        tmpMessages.add(String.format("%s의 연령 제한이 %s에서 %s으로 변경되었습니다.",
            g.getTitle(), oldRating, g.getContentRating()));
      }
      if (!tmpMessages.isEmpty()) {
        StringBuilder builder = new StringBuilder();
        builder.append(cartGame.getTitle()).append(" 게임 변동 사항: ");
        builder.append(String.join(", ", tmpMessages));
        response.getMessages().add(builder.toString());
      }

    }
    cartService.putCart(cart.getUserId(), cart);
    return new CartResponse(cart, messages);
  }

  public void clearCart(Long userId) {
    cartService.clearCart(userId);
  }
}
