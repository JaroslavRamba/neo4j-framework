/*
 * Copyright (c) 2014 GraphAware
 *
 * This file is part of GraphAware.
 *
 * GraphAware is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 *  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received a copy of
 * the GNU General Public License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package com.graphaware.writer;

import java.util.concurrent.Callable;

/**
 * A database writer that writes to the database in a way that prevents deadlocks.
 * <p/>
 * Implementations can choose how they write to the database, but must make sure that tasks that are submitted to it
 * run within the context of a transaction.
 */
public interface DatabaseWriter {

    /**
     * Start the writer.
     */
    void start();

    /**
     * Stop the writer.
     */
    void stop();

    /**
     * Write to the database without waiting for the result of the write.
     *
     * @param task     that writes to the database.
     */
    void write(Runnable task);

    /**
     * Write to the database without waiting for the result of the write.
     *
     * @param task     that writes to the database.
     * @param id       of the task for logging purposes.
     */
    void write(Runnable task, String id);

    /**
     * Write to the database.
     *
     * @param task       that writes to the database and returns a result.
     * @param id         of the task for logging purposes.
     * @param waitMillis maximum number of ms to wait for the task to be executed.
     * @param <T>        type of the tasks return value.
     * @return value returned by the task. <code>null</code> of the tasks didn't complete in the specified waiting time,
     *         or if it didn't execute successfully.
     */
    <T> T write(Callable<T> task, String id, int waitMillis);
}
