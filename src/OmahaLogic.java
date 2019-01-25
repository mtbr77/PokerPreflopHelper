public class OmahaLogic extends GameLogic {
//    public OmahaLogic(String tableId) {
//            this.tableId = tableId;
//    }

    @Override
    public void determineMove(GameTable table) {

    }

    @Override
    public boolean holeCardsIsPreliminaryBadPreflop(Game game) {
        OmahaHoleCards holeCards = (OmahaHoleCards) game.getHoleCards();
        //int position = game.getMyPosition();
        //boolean isZoom = game.isZoom();
        //System.out.println("POS= " + position);

        if(holeCards.getHatchisonScore() < 28) {
            return true;
        }
        return false;
    }


}
