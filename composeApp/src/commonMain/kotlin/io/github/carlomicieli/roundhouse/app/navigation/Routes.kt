/*
 *   Copyright (c) 2025 (C) Carlo Micieli
 *
 *    Licensed to the Apache Software Foundation (ASF) under one
 *    or more contributor license agreements.  See the NOTICE file
 *    distributed with this work for additional information
 *    regarding copyright ownership.  The ASF licenses this file
 *    to you under the Apache License, Version 2.0 (the
 *    "License"); you may not use this file except in compliance
 *    with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing,
 *    software distributed under the License is distributed on an
 *    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *    KIND, either express or implied.  See the License for the
 *    specific language governing permissions and limitations
 *    under the License.
 */
package io.github.carlomicieli.roundhouse.app.navigation

/**
 * Placeholder route definitions for the app navigation.
 * Each route currently renders a simple placeholder showing the displayName.
 * TODO: replace placeholders with real screens.
 */
sealed class Route(
    val route: String,
    val displayName: String,
) {
    object Home : Route("home", "Home")

    object Collection : Route("collection", "Collection")

    object WishLists : Route("wishlists", "Wish Lists")

    object Depot : Route("depot", "Depot")

    object Consists : Route("consists", "Consists")

    companion object {
        fun fromRoute(route: String?): Route? =
            when (route) {
                Home.route -> Home
                Collection.route -> Collection
                WishLists.route -> WishLists
                Depot.route -> Depot
                Consists.route -> Consists
                else -> null
            }

        val all: List<Route> = listOf(Home, Collection, WishLists, Depot, Consists)
    }
}
