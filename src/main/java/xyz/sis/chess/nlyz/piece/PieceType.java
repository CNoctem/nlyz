package xyz.sis.chess.nlyz.piece;

public enum PieceType {

    K {
        @Override
        public Piece create(XColor color) {
            return new King(this, color);
        }
    }, Q {
        @Override
        public Piece create(XColor color) {
            return new Queen(this, color);
        }
    }, R {
        @Override
        public Piece create(XColor color) {
            return new Rook(this, color);
        }
    }, N {
        @Override
        public Piece create(XColor color) {
            return new Night(this, color);
        }
    }, B {
        @Override
        public Piece create(XColor color) {
            return new Bishop(this, color);
        }
    }, P {
        @Override
        public Piece create(XColor color) {
            return new Pawn(this, color);
        }
    }, E {
        @Override
        public Piece create(XColor color) {
            return EMPTY_PIECE;
        }
    };

    private static final EmptyPiece EMPTY_PIECE = new EmptyPiece(PieceType.E, null);

    public abstract Piece create(XColor color);

}
