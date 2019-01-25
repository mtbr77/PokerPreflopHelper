public class GameMove {
    private GameAction action;
    private int bbAmount ;
    private double moneyAmount;

    public GameAction getActionType() {
        return action;
    }


    public synchronized void setGameAction(GameAction action) {
        this.action = action;
        System.out.println("setAction=" + this.action);
    }

    public int getAmount() {
        return bbAmount;
    }
    public double getMoney() { return moneyAmount; }
    public void setAmount(int bb) { this.bbAmount = bb; }
    public void setMoneyAmount(double money) { this.moneyAmount = money; }
}
