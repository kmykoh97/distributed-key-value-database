// Ref: https://github.com/rqlite/rqlite/blob/master/auth/credential_store_test.go
package auth

import (
	"strings"
	"testing"
)

func Test_AuthLoadSingle(t *testing.T) {
	const jsonStream = `
        [
            {"username": "username1", "password": "password1"}
        ]
    `

	store := NewCredentialsStore()
	if err := store.Load(strings.NewReader(jsonStream)); err != nil {
		t.Fatalf("failed to load single credential: %s", err.Error())
	}

	if check := store.Check("username1", "password1"); !check {
		t.Fatalf("single credential not loaded correctly")
	}
	if check := store.Check("username1", "wrong"); check {
		t.Fatalf("single credential not loaded correctly")
	}

	if check := store.Check("wrong", "password1"); check {
		t.Fatalf("single credential not loaded correctly")
	}
	if check := store.Check("wrong", "wrong"); check {
		t.Fatalf("single credential not loaded correctly")
	}
}

func Test_AuthLoadMultiple(t *testing.T) {
	const jsonStream = `
        [
            {"username": "username1", "password": "password1"},
            {"username": "username2", "password": "password2"}
        ]
    `

	store := NewCredentialsStore()
	if err := store.Load(strings.NewReader(jsonStream)); err != nil {
		t.Fatalf("failed to load multiple credentials: %s", err.Error())
	}

	if check := store.Check("username1", "password1"); !check {
		t.Fatalf("username1 credential not loaded correctly")
	}
	if check := store.Check("username1", "password2"); check {
		t.Fatalf("username1 credential not loaded correctly")
	}

	if check := store.Check("username2", "password2"); !check {
		t.Fatalf("username2 credential not loaded correctly")
	}
	if check := store.Check("username2", "password1"); check {
		t.Fatalf("username2 credential not loaded correctly")
	}

	if check := store.Check("username1", "wrong"); check {
		t.Fatalf("multiple credential not loaded correctly")
	}
	if check := store.Check("wrong", "password1"); check {
		t.Fatalf("multiple credential not loaded correctly")
	}
	if check := store.Check("wrong", "wrong"); check {
		t.Fatalf("multiple credential not loaded correctly")
	}
}

func Test_AuthPermsLoadSingle(t *testing.T) {
	const jsonStream = `
        [
            {
                "username": "username1",
                "password": "password1",
                "perms": ["foo", "bar"]
            },
            {
                "username": "username2",
                "password": "password1",
                "perms": ["baz"]
            }
        ]
    `

	store := NewCredentialsStore()
	if err := store.Load(strings.NewReader(jsonStream)); err != nil {
		t.Fatalf("failed to load single credential: %s", err.Error())
	}

	if check := store.Check("username1", "password1"); !check {
		t.Fatalf("single credential not loaded correctly")
	}
	if check := store.Check("username1", "wrong"); check {
		t.Fatalf("single credential not loaded correctly")
	}

	if perm := store.HasPerm("wrong", "foo"); perm {
		t.Fatalf("wrong has foo perm")
	}

	if perm := store.HasPerm("username1", "foo"); !perm {
		t.Fatalf("username1 does not have foo perm")
	}
	if perm := store.HasPerm("username1", "bar"); !perm {
		t.Fatalf("username1 does not have bar perm")
	}
	if perm := store.HasPerm("username1", "baz"); perm {
		t.Fatalf("username1 does have baz perm")
	}

	if perm := store.HasPerm("username2", "baz"); !perm {
		t.Fatalf("username1 does not have baz perm")
	}
}

func Test_AuthPermsEmptyLoadSingle(t *testing.T) {
	const jsonStream = `
        [
            {
                "username": "username1",
                "password": "password1",
                "perms": []
            }
        ]
    `

	store := NewCredentialsStore()
	if err := store.Load(strings.NewReader(jsonStream)); err != nil {
		t.Fatalf("failed to load single credential: %s", err.Error())
	}

	if check := store.Check("username1", "password1"); !check {
		t.Fatalf("single credential not loaded correctly")
	}
	if check := store.Check("username1", "wrong"); check {
		t.Fatalf("single credential not loaded correctly")
	}

	if perm := store.HasPerm("username1", "foo"); perm {
		t.Fatalf("wrong has foo perm")
	}
}

func Test_AuthPermsNilLoadSingle(t *testing.T) {
	const jsonStream = `
        [
            {
                "username": "username1",
                "password": "password1"
            }
        ]
    `

	store := NewCredentialsStore()
	if err := store.Load(strings.NewReader(jsonStream)); err != nil {
		t.Fatalf("failed to load single credential: %s", err.Error())
	}

	if check := store.Check("username1", "password1"); !check {
		t.Fatalf("single credential not loaded correctly")
	}
	if check := store.Check("username1", "wrong"); check {
		t.Fatalf("single credential not loaded correctly")
	}

	if perm := store.HasPerm("username1", "foo"); perm {
		t.Fatalf("wrong has foo perm")
	}
}
