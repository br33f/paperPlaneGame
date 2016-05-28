package game;

import adapters.MysqlAdapter;

/**
 * Connector class.
 * Making connection in new thread.
 */
public class Connector implements Runnable {
    //attributes
    private MysqlAdapter adapter = null;

    //methods

    @Override
    public void run() {
        this.adapter = new MysqlAdapter("mysqlconfig.properties", false);
    }

    /**
     * Getter for MySql adapter
     * @return this.adapter - current adapter.
     */
    public MysqlAdapter getDbAdapter(){
        return this.adapter;
    }
}
