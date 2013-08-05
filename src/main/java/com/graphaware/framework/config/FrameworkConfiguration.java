/*
 * Copyright (c) 2013 GraphAware
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

package com.graphaware.framework.config;

/**
 * {@link com.graphaware.framework.GraphAwareFramework} configuration.
 */
public interface FrameworkConfiguration {

    /**
     * Prefix for GraphAware internal nodes, relationships, and properties. This is fixed as there is little chance
     * that users would have a reason to change it.
     */
    public static final String GA_PREFIX = "_GA_";

    /**
     * Key of a property present on GraphAware internal nodes. This is fixed as there is little chance
     * that users would have a reason to change it.
     */
    public static final String GA_NODE_PROPERTY_KEY = GA_PREFIX + "LABEL";

    /**
     * Default value for {@link #separator()}.
     */
    static final String DEFAULT_SEPARATOR = "#";

    /**
     * Get the separator used as an information delimiter for String-convertible nodes, relationships, and properties.
     *
     * @return separator.
     */
    String separator();

    /**
     * Create prefix a component should use for internal data it reads/writes (nodes, relationships, properties).
     *
     * @param id of the component/module.
     * @return prefix of the form {@link #GA_PREFIX} + id + "_"
     */
    String createPrefix(String id);
}