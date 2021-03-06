/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wesleyelliott.weather.data

val mockUrbanLocations = listOf(
    Location(
        name = "Tokyo",
        country = "Japan",
        imageUrl = "https://images.pexels.com/photos/2614818/pexels-photo-2614818.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
    ),
    Location(
        name = "Paris",
        country = "France",
        imageUrl = "https://images.pexels.com/photos/3214982/pexels-photo-3214982.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500"
    ),
    Location(
        name = "New York",
        country = "USA",
        imageUrl = "https://images.pexels.com/photos/3889855/pexels-photo-3889855.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
    ),
    Location(
        name = "Sydney",
        country = "Australia",
        imageUrl = "https://images.pexels.com/photos/1878293/pexels-photo-1878293.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
    ),
    Location(
        name = "Rio",
        country = "Brazil",
        imageUrl = "https://images.pexels.com/photos/3648269/pexels-photo-3648269.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
    ),
)

val mockNatureLocations = listOf(
    Location(
        name = "Cape Town",
        country = "South Africa",
        imageUrl = "https://images.pexels.com/photos/963713/pexels-photo-963713.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500"
    ),
    Location(
        name = "Grand Canyon",
        country = "USA",
        imageUrl = "https://images.pexels.com/photos/2542340/pexels-photo-2542340.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
    ),
    Location(
        name = "Cappadocia",
        country = "Turkey",
        imageUrl = "https://images.pexels.com/photos/2563593/pexels-photo-2563593.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500"
    ),
    Location(
        name = "Namib Desert",
        country = "Namibia",
        imageUrl = "https://images.pexels.com/photos/3714898/pexels-photo-3714898.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500"
    ),
    Location(
        name = "Cliffs of Moher",
        country = "Ireland",
        imageUrl = "https://images.pexels.com/photos/2382681/pexels-photo-2382681.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
    ),
)

val mockCoastalLocations = listOf(
    Location(
        name = "Naples",
        country = "Italy",
        imageUrl = "https://images.pexels.com/photos/2972658/pexels-photo-2972658.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
    ),
    Location(
        name = "San Fransisco",
        country = "USA",
        imageUrl = "https://images.pexels.com/photos/1006965/pexels-photo-1006965.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
    ),
    Location(
        name = "Dubai",
        country = "U.A.E",
        imageUrl = "https://images.pexels.com/photos/4491951/pexels-photo-4491951.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
    ),
    Location(
        name = "Sydney",
        country = "Australia",
        imageUrl = "https://images.pexels.com/photos/1878293/pexels-photo-1878293.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
    ),
)

val mockMountainLocations = listOf(
    Location(
        name = "The Alps",
        country = "Italy",
        imageUrl = "https://images.pexels.com/photos/2437296/pexels-photo-2437296.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
    ),
    Location(
        name = "The Fjords",
        country = "Norway",
        imageUrl = "https://images.pexels.com/photos/1562058/pexels-photo-1562058.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
    ),
)

val mockLocations = mapOf(
    EnvironmentOption.Urban to mockUrbanLocations,
    EnvironmentOption.Nature to mockNatureLocations,
    EnvironmentOption.Coastal to mockCoastalLocations,
    EnvironmentOption.Mountains to mockMountainLocations,
)

val mockDistances = mapOf(
    DistanceOption._100 to (0..100),
    DistanceOption._250 to (100..250),
    DistanceOption._500 to (250..500),
    DistanceOption.Any to (0..500),
)

val mockTemperatures = mapOf(
    TemperatureOption.Warm to (20..32),
    TemperatureOption.Cold to (-20..10),
    TemperatureOption.Any to (-20..32),
)
