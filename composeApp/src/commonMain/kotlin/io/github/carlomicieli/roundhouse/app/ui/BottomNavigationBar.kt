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
package io.github.carlomicieli.roundhouse.app.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import io.github.carlomicieli.roundhouse.app.navigation.Route

/**
 * Bottom navigation with five destinations. Each item has a test tag as required.
 */
@Composable
fun BottomNavigationBar(
    currentRoute: Route?,
    onNavigate: (Route) -> Unit,
) {
    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        NavigationBarItem(
            selected = currentRoute == Route.Home,
            onClick = { onNavigate(Route.Home) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            modifier = Modifier.testTag("nav_my_home"),
        )

        NavigationBarItem(
            selected = currentRoute == Route.Collection,
            onClick = { onNavigate(Route.Collection) },
            icon = { Icon(Icons.Default.List, contentDescription = "Collection") },
            label = { Text("Collection") },
            modifier = Modifier.testTag("nav_my_collection"),
        )

        NavigationBarItem(
            selected = currentRoute == Route.WishLists,
            onClick = { onNavigate(Route.WishLists) },
            icon = { Icon(Icons.Default.Favorite, contentDescription = "Wish Lists") },
            label = { Text("Wish Lists") },
            modifier = Modifier.testTag("nav_my_wishlists"),
        )

        NavigationBarItem(
            selected = currentRoute == Route.Depot,
            onClick = { onNavigate(Route.Depot) },
            icon = { Icon(Icons.Default.Inventory, contentDescription = "Depot") },
            label = { Text("Depot") },
            modifier = Modifier.testTag("nav_my_depot"),
        )

        NavigationBarItem(
            selected = currentRoute == Route.Consists,
            onClick = { onNavigate(Route.Consists) },
            icon = { Icon(Icons.Default.ViewList, contentDescription = "Consists") },
            label = { Text("Consists") },
            modifier = Modifier.testTag("nav_my_favourites"),
        )
    }
}
