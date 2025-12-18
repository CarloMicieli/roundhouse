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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

/**
 * Nav graph with placeholder screens.
 * Uses navigation-compose (KMP-ready) and simple Text placeholders.
 */
@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: Route,
) {
    NavHost(navController = navController, startDestination = startDestination.route) {
        composable(Route.Home.route) {
            PlaceholderScreen(Route.Home.displayName)
        }
        composable(Route.Collection.route) {
            PlaceholderScreen(Route.Collection.displayName)
        }
        composable(Route.WishLists.route) {
            PlaceholderScreen(Route.WishLists.displayName)
        }
        composable(Route.Depot.route) {
            PlaceholderScreen(Route.Depot.displayName)
        }
        composable(Route.Consists.route) {
            PlaceholderScreen(Route.Consists.displayName)
        }
    }
}

@Composable
private fun PlaceholderScreen(name: String) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = name, fontSize = 20.sp, textAlign = TextAlign.Center)
        }
    }
}
