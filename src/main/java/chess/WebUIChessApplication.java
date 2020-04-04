package chess;

import chess.controller.ChessController;
import chess.controller.dto.ChessBoardDto;
import chess.controller.dto.RequestDto;
import chess.controller.dto.ResponseDto;
import chess.domain.chesspiece.Piece;
import chess.domain.game.ChessBoard;
import chess.domain.game.Command;
import chess.domain.game.PieceFactory;
import chess.domain.position.Position;
import com.google.gson.Gson;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class WebUIChessApplication {
    private static Gson gson = new Gson();
    private static final ChessController chessController = new ChessController();

    public static void main(String[] args) {
        staticFiles.location("templates");

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return render(model, "index.html");
        });

        get("/init", (req, res) -> {
            return gson.toJson(chessController.start(new RequestDto(Command.START)));
        });

        get("/move", (req, res) -> {
            RequestDto requestDto = new RequestDto(Command.MOVE, req);
            ResponseDto responseDto = chessController.move(requestDto);
            return gson.toJson(responseDto);
        });
    }

    private static String render(Map<String, Object> model, String templatePath) {
        return new HandlebarsTemplateEngine().render(new ModelAndView(model, templatePath));
    }
}
