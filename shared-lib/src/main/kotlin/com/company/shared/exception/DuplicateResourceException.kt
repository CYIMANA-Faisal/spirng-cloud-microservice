package com.company.shared.exception

class DuplicateResourceException(
    resourceName: String,
    fieldName: String,
    fieldValue: Any
) : RuntimeException("$resourceName already exists with $fieldName: '$fieldValue'")
