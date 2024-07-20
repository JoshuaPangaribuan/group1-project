package com.group1.common.uid;

public sealed interface IUIDGenerator<T> permits UIDNumberGenerator {
    T Generate();
}
