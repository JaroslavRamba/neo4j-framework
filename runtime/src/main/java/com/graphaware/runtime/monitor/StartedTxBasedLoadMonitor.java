package com.graphaware.runtime.monitor;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.GraphDatabaseAPI;
import org.neo4j.kernel.impl.transaction.TxManager;

/**
 * {@link DatabaseLoadMonitor} returning the database load based on the number of transactions started in a period of time.
 * <p/>
 * The load is measured as the average load in a configurable {@link RunningWindowAverage}.
 * <p/>
 * Samples are taken as the monitor is queried.
 */
public class StartedTxBasedLoadMonitor implements DatabaseLoadMonitor {

    private final TxManager txManager;
    private final RunningWindowAverage runningWindowAverage;

    /**
     * Construct a new monitor.
     *
     * @param database             to monitor.
     * @param runningWindowAverage to use for the monitoring.
     */
    public StartedTxBasedLoadMonitor(GraphDatabaseService database, RunningWindowAverage runningWindowAverage) {
        this.txManager = ((GraphDatabaseAPI) database).getDependencyResolver().resolveDependency(TxManager.class);
        this.runningWindowAverage = runningWindowAverage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLoad() {
        runningWindowAverage.sample(System.currentTimeMillis(), txManager.getStartedTxCount());
        return runningWindowAverage.getAverage();
    }
}