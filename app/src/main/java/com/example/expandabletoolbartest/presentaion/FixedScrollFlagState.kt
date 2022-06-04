package com.example.expandabletoolbartest.presentaion

abstract class FixedScrollFlagState(heightRange: IntRange): ScrollFlagState(heightRange) {
    final override val offset: Float = 0f
}