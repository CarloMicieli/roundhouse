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
package io.github.carlomicieli.roundhouse

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.carlomicieli.roundhouse.app.navigation.AppNavHost
import io.github.carlomicieli.roundhouse.app.navigation.Route
import io.github.carlomicieli.roundhouse.app.ui.AppTopBar
import io.github.carlomicieli.roundhouse.app.ui.BottomNavigationBar
import io.github.carlomicieli.roundhouse.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    AppTheme {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = Route.fromRoute(navBackStackEntry?.destination?.route)

        Scaffold(
            modifier =
                Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .safeContentPadding()
                    .fillMaxSize(),
            topBar = { AppTopBar(currentRoute) { /* TODO: handle overflow */ } },
            bottomBar = {
                BottomNavigationBar(currentRoute) { route ->
                    // navigate with single top to avoid multiple copies
                    navController.navigate(route.route) {
                        launchSingleTop = true
                    }
                }
            },
        ) { innerPadding ->
            // NavHost content — apply inner padding
            Box(modifier = Modifier.padding(innerPadding)) {
                AppNavHost(navController = navController, startDestination = Route.Home)
            }
        }
    }
}
