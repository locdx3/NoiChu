@file:Suppress("UNCHECKED_CAST")

package com.codedao.noichu.common.extension

import com.codedao.noichu.data.firebase.entity.ChangePayload
import com.google.firebase.database.*
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.StorageReference
import io.reactivex.Observable
import io.reactivex.Observable.create
import io.reactivex.disposables.Disposables
import jp.drjoy.app.data.repository.entity.VoidEntity

fun DatabaseReference.getItemSingle(): Observable<DataSnapshot> {
    return Observable.create<DataSnapshot> { emitter ->
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                emitter.checkDisposed()?.onError(p0.toException())
            }

            override fun onDataChange(p0: DataSnapshot) {
                emitter.checkDisposed()?.onNext(p0)
                emitter.checkDisposed()?.onComplete()
            }
        }
        emitter.setDisposable(Disposables.fromAction {
            this.removeEventListener(valueEventListener)
        })
        this.addListenerForSingleValueEvent(valueEventListener)
    }
}

fun DatabaseReference.getItem(): Observable<DataSnapshot> {
    return Observable.create<DataSnapshot> { emitter ->
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                emitter.checkDisposed()?.onError(p0.toException())
            }

            override fun onDataChange(p0: DataSnapshot) {
                emitter.checkDisposed()?.onNext(p0)
            }
        }
        emitter.setDisposable(Disposables.fromAction {
            this.removeEventListener(valueEventListener)
        })
        this.addValueEventListener(valueEventListener)
    }
}

fun DatabaseReference.getListItemSingle(): Observable<List<DataSnapshot>> {
    return Observable.create { emitter ->
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                emitter.checkDisposed()?.onError(p0.toException())
            }

            override fun onDataChange(p0: DataSnapshot) {
                val children = if (p0.hasChildren()) {
                    p0.children.toList()
                } else {
                    listOf()
                }
                emitter.checkDisposed()?.onNext(children)
                emitter.checkDisposed()?.onComplete()
            }
        }
        emitter.setDisposable(Disposables.fromAction {
            this.removeEventListener(valueEventListener)
        })
        this.addListenerForSingleValueEvent(valueEventListener)
    }
}

fun <T> DatabaseReference.setChildValue(key: String, value: T?): Observable<VoidEntity> {
    return Observable.create { emitter ->
        this.child(key).setValue(value) { p0, _ ->
            if (p0 == null) {
                emitter.checkDisposed()?.onNext(VoidEntity())
                emitter.checkDisposed()?.onComplete()
            } else {
                emitter.checkDisposed()?.onError(p0.toException())
            }
        }
    }
}

fun DatabaseReference.removeChildValue(key: String): Observable<String> {
    return Observable.create { emitter ->
        this.child(key).removeValue { databaseError, ref ->
            if (databaseError == null) {
                emitter.checkDisposed()?.onNext(ref.key!!)
                emitter.checkDisposed()?.onComplete()
            } else {
                emitter.checkDisposed()?.onError(databaseError.toException())
            }
        }
    }
}

fun StorageReference.getFileInfo(): Observable<StorageMetadata> {
    return Observable.create<StorageMetadata> { emitter ->
        this.metadata.addOnSuccessListener { metadata ->
            emitter.checkDisposed()?.onNext(metadata)
            emitter.checkDisposed()?.onComplete()
        }.addOnFailureListener { exception ->
            emitter.checkDisposed()?.onError(exception)
        }
    }
}

fun DatabaseReference.getCountSingle(predicate: ((dataSnapshot: DataSnapshot) -> Boolean)? = null): Observable<Int> {
    return create { emitter ->
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                emitter.checkDisposed()?.onError(p0.toException())
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (predicate == null) {
                    emitter.checkDisposed()?.onNext(p0.children.count())
                } else {
                    val count = p0.children.count(predicate)
                    emitter.checkDisposed()?.onNext(count)
                }
                emitter.checkDisposed()?.onComplete()
            }
        }
        emitter.setDisposable(Disposables.fromAction {
            this.removeEventListener(valueEventListener)
        })
        this.addListenerForSingleValueEvent(valueEventListener)
    }
}

fun DatabaseReference.getCount(predicate: ((dataSnapshot: DataSnapshot) -> Boolean)? = null): Observable<Int> {
    return create { emitter ->
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                emitter.checkDisposed()?.onError(p0.toException())
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (predicate == null) {
                    emitter.checkDisposed()?.onNext(p0.children.count())
                } else {
                    val count = p0.children.count(predicate)
                    emitter.checkDisposed()?.onNext(count)
                }
            }
        }
        emitter.setDisposable(Disposables.fromAction {
            this.removeEventListener(valueEventListener)
        })
        this.addValueEventListener(valueEventListener)
    }
}

fun <T> DatabaseReference.pushChildValue(value: T?): Observable<String> {
    return Observable.create { emitter ->
        this.push().setValue(value) { p0, ref ->
            if (p0 == null) {
                emitter.checkDisposed()?.onNext(ref.key!!)
                emitter.checkDisposed()?.onComplete()
            } else {
                emitter.checkDisposed()?.onError(p0.toException())
            }
        }
    }
}

fun DatabaseReference.updateChildrenValues(mutableMap: Map<String, Any?>): Observable<VoidEntity> {
    return Observable.create<VoidEntity> { emitter ->
        this.updateChildren(mutableMap) { error, _ ->
            if (error == null) {
                emitter.checkDisposed()?.onNext(VoidEntity())
                emitter.checkDisposed()?.onComplete()
            } else {
                emitter.checkDisposed()?.onError(error.toException())
            }
        }
    }
}


fun Query.listenChild(): Observable<ChangePayload<DataSnapshot>> {
    return Observable.create<ChangePayload<DataSnapshot>> { emitter ->
        val childEventListener = object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
                emitter.checkDisposed()?.onError(p0.toException())
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                //do nothing
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                emitter.checkDisposed()?.onNext(ChangePayload(ChangePayload.CHANGED, p0))
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                emitter.checkDisposed()?.onNext(ChangePayload(ChangePayload.ADDED, p0))
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                emitter.checkDisposed()?.onNext(ChangePayload(ChangePayload.REMOVED, p0))
            }
        }
        emitter.setDisposable(Disposables.fromAction {
            this.removeEventListener(childEventListener)
        })
        this.addChildEventListener(childEventListener)
    }
}

fun Query.getItemSingle(): Observable<DataSnapshot> {
    return create { emitter ->
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                emitter.checkDisposed()?.onError(p0.toException())
            }

            override fun onDataChange(p0: DataSnapshot) {
                emitter.checkDisposed()?.onNext(p0)
                emitter.checkDisposed()?.onComplete()
            }
        }
        emitter.setDisposable(Disposables.fromAction {
            this.removeEventListener(valueEventListener)
        })
        this.addListenerForSingleValueEvent(valueEventListener)
    }
}

fun Query.getListItemSingle(): Observable<List<DataSnapshot>> {
    return create { emitter ->
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                emitter.checkDisposed()?.onError(p0.toException())
            }

            override fun onDataChange(p0: DataSnapshot) {
                val items = if (p0.hasChildren()) {
                    p0.children.toList()
                } else {
                    listOf()
                }
                emitter.checkDisposed()?.onNext(items)
                emitter.checkDisposed()?.onComplete()
            }
        }
        emitter.setDisposable(Disposables.fromAction {
            this.removeEventListener(valueEventListener)
        })
        this.addListenerForSingleValueEvent(valueEventListener)
    }
}

fun Query.getItem(): Observable<DataSnapshot> {
    return Observable.create<DataSnapshot> { emitter ->
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                emitter.checkDisposed()?.onError(p0.toException())
            }

            override fun onDataChange(p0: DataSnapshot) {
                emitter.checkDisposed()?.onNext(p0)
            }
        }
        emitter.setDisposable(Disposables.fromAction {
            this.removeEventListener(valueEventListener)
        })
        this.addValueEventListener(valueEventListener)
    }
}

fun Query.getListItem(): Observable<List<DataSnapshot>> {
    return Observable.create<List<DataSnapshot>> { emitter ->
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                emitter.checkDisposed()?.onError(p0.toException())
            }

            override fun onDataChange(p0: DataSnapshot) {
                val items = if (p0.hasChildren()) {
                    p0.children.toList()
                } else {
                    listOf()
                }
                emitter.checkDisposed()?.onNext(items)
            }
        }
        emitter.setDisposable(Disposables.fromAction {
            this.removeEventListener(valueEventListener)
        })
        this.addValueEventListener(valueEventListener)
    }
}

fun Query.getCountSingle(predicate: ((dataSnapshot: DataSnapshot) -> Boolean)? = null): Observable<Int> {
    return create { emitter ->
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                emitter.checkDisposed()?.onError(p0.toException())
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (predicate == null) {
                    emitter.checkDisposed()?.onNext(p0.children.count())
                } else {
                    val count = p0.children.count(predicate)
                    emitter.checkDisposed()?.onNext(count)
                }
                emitter.checkDisposed()?.onComplete()
            }
        }
        emitter.setDisposable(Disposables.fromAction {
            this.removeEventListener(valueEventListener)
        })
        this.addListenerForSingleValueEvent(valueEventListener)
    }
}

fun Query.getCount(predicate: ((dataSnapshot: DataSnapshot) -> Boolean)? = null): Observable<Int> {
    return create { emitter ->
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                emitter.checkDisposed()?.onError(p0.toException())
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (predicate == null) {
                    emitter.checkDisposed()?.onNext(p0.children.count())
                } else {
                    val count = p0.children.count(predicate)
                    emitter.checkDisposed()?.onNext(count)
                }
            }
        }
        emitter.setDisposable(Disposables.fromAction {
            this.removeEventListener(valueEventListener)
        })
        this.addValueEventListener(valueEventListener)
    }
}

fun <T> Observable<DataSnapshot>.mapFirebase(mapper: (data: DataSnapshot) -> T?): Observable<T> {
    return this.filter { it.exists() }.map { mapper(it) }
}

fun <T> Observable<DataSnapshot>.mapFirebaseHasNull(mapper: (data: DataSnapshot) -> T): Observable<T> {
    return this.map { mapper(it) }
}

fun <T> Observable<List<DataSnapshot>>.mapIterableFirebase(mapper: (data: DataSnapshot) -> T?): Observable<List<T>> {
    return this.map { listData ->
        val result = ArrayList<T>()

        listData.forEach {
            if (it.exists()) {
                result.add(mapper(it)!!)
            }
        }

        return@map result
    }
}

fun <T> Observable<ChangePayload<DataSnapshot>>.mapListenChildFirebase(converter: (type: Int, data: DataSnapshot) -> T): Observable<ChangePayload<T>> {
    return this.map { (type, model) ->
        val item = converter(type, model)
        return@map ChangePayload(type, item)
    }
}

