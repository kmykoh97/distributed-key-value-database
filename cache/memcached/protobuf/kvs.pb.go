// Code generated by protoc-gen-go.
// source: kvs.proto
// DO NOT EDIT!

/*
Package protobuf is a generated protocol buffer package.

It is generated from these files:
	kvs.proto

It has these top-level messages:
	KeyValuePair
	Key
	Namespace
	Response
	CountResponse
	ShowKeysResponse
	ShowDataResponse
	ShowNamespacesResponse
	NamespaceResponse
*/
package protobuf

import proto "github.com/golang/protobuf/proto"
import fmt "fmt"
import math "math"
import google_protobuf "github.com/golang/protobuf/ptypes/empty"

import (
	context "golang.org/x/net/context"
	grpc "google.golang.org/grpc"
)

// Reference imports to suppress errors if they are not otherwise used.
var _ = proto.Marshal
var _ = fmt.Errorf
var _ = math.Inf

// This is a compile-time assertion to ensure that this generated file
// is compatible with the proto package it is being compiled against.
// A compilation error at this line likely means your copy of the
// proto package needs to be updated.
const _ = proto.ProtoPackageIsVersion2 // please upgrade the proto package

type KeyValuePair struct {
	Key   string `protobuf:"bytes,1,opt,name=key" json:"key,omitempty"`
	Value string `protobuf:"bytes,2,opt,name=value" json:"value,omitempty"`
}

func (m *KeyValuePair) Reset()                    { *m = KeyValuePair{} }
func (m *KeyValuePair) String() string            { return proto.CompactTextString(m) }
func (*KeyValuePair) ProtoMessage()               {}
func (*KeyValuePair) Descriptor() ([]byte, []int) { return fileDescriptor0, []int{0} }

func (m *KeyValuePair) GetKey() string {
	if m != nil {
		return m.Key
	}
	return ""
}

func (m *KeyValuePair) GetValue() string {
	if m != nil {
		return m.Value
	}
	return ""
}

type Key struct {
	Key string `protobuf:"bytes,1,opt,name=key" json:"key,omitempty"`
}

func (m *Key) Reset()                    { *m = Key{} }
func (m *Key) String() string            { return proto.CompactTextString(m) }
func (*Key) ProtoMessage()               {}
func (*Key) Descriptor() ([]byte, []int) { return fileDescriptor0, []int{1} }

func (m *Key) GetKey() string {
	if m != nil {
		return m.Key
	}
	return ""
}

type Namespace struct {
	Namespace string `protobuf:"bytes,1,opt,name=namespace" json:"namespace,omitempty"`
}

func (m *Namespace) Reset()                    { *m = Namespace{} }
func (m *Namespace) String() string            { return proto.CompactTextString(m) }
func (*Namespace) ProtoMessage()               {}
func (*Namespace) Descriptor() ([]byte, []int) { return fileDescriptor0, []int{2} }

func (m *Namespace) GetNamespace() string {
	if m != nil {
		return m.Namespace
	}
	return ""
}

type Response struct {
	Success bool   `protobuf:"varint,1,opt,name=success" json:"success,omitempty"`
	Value   string `protobuf:"bytes,2,opt,name=value" json:"value,omitempty"`
}

func (m *Response) Reset()                    { *m = Response{} }
func (m *Response) String() string            { return proto.CompactTextString(m) }
func (*Response) ProtoMessage()               {}
func (*Response) Descriptor() ([]byte, []int) { return fileDescriptor0, []int{3} }

func (m *Response) GetSuccess() bool {
	if m != nil {
		return m.Success
	}
	return false
}

func (m *Response) GetValue() string {
	if m != nil {
		return m.Value
	}
	return ""
}

type CountResponse struct {
	Count int32 `protobuf:"varint,1,opt,name=count" json:"count,omitempty"`
}

func (m *CountResponse) Reset()                    { *m = CountResponse{} }
func (m *CountResponse) String() string            { return proto.CompactTextString(m) }
func (*CountResponse) ProtoMessage()               {}
func (*CountResponse) Descriptor() ([]byte, []int) { return fileDescriptor0, []int{4} }

func (m *CountResponse) GetCount() int32 {
	if m != nil {
		return m.Count
	}
	return 0
}

type ShowKeysResponse struct {
	Keys []string `protobuf:"bytes,1,rep,name=keys" json:"keys,omitempty"`
}

func (m *ShowKeysResponse) Reset()                    { *m = ShowKeysResponse{} }
func (m *ShowKeysResponse) String() string            { return proto.CompactTextString(m) }
func (*ShowKeysResponse) ProtoMessage()               {}
func (*ShowKeysResponse) Descriptor() ([]byte, []int) { return fileDescriptor0, []int{5} }

func (m *ShowKeysResponse) GetKeys() []string {
	if m != nil {
		return m.Keys
	}
	return nil
}

type ShowDataResponse struct {
	Data []*KeyValuePair `protobuf:"bytes,1,rep,name=data" json:"data,omitempty"`
}

func (m *ShowDataResponse) Reset()                    { *m = ShowDataResponse{} }
func (m *ShowDataResponse) String() string            { return proto.CompactTextString(m) }
func (*ShowDataResponse) ProtoMessage()               {}
func (*ShowDataResponse) Descriptor() ([]byte, []int) { return fileDescriptor0, []int{6} }

func (m *ShowDataResponse) GetData() []*KeyValuePair {
	if m != nil {
		return m.Data
	}
	return nil
}

type ShowNamespacesResponse struct {
	Namespaces []string `protobuf:"bytes,1,rep,name=namespaces" json:"namespaces,omitempty"`
}

func (m *ShowNamespacesResponse) Reset()                    { *m = ShowNamespacesResponse{} }
func (m *ShowNamespacesResponse) String() string            { return proto.CompactTextString(m) }
func (*ShowNamespacesResponse) ProtoMessage()               {}
func (*ShowNamespacesResponse) Descriptor() ([]byte, []int) { return fileDescriptor0, []int{7} }

func (m *ShowNamespacesResponse) GetNamespaces() []string {
	if m != nil {
		return m.Namespaces
	}
	return nil
}

type NamespaceResponse struct {
	Token string `protobuf:"bytes,1,opt,name=token" json:"token,omitempty"`
}

func (m *NamespaceResponse) Reset()                    { *m = NamespaceResponse{} }
func (m *NamespaceResponse) String() string            { return proto.CompactTextString(m) }
func (*NamespaceResponse) ProtoMessage()               {}
func (*NamespaceResponse) Descriptor() ([]byte, []int) { return fileDescriptor0, []int{8} }

func (m *NamespaceResponse) GetToken() string {
	if m != nil {
		return m.Token
	}
	return ""
}

func init() {
	proto.RegisterType((*KeyValuePair)(nil), "protobuf.KeyValuePair")
	proto.RegisterType((*Key)(nil), "protobuf.Key")
	proto.RegisterType((*Namespace)(nil), "protobuf.Namespace")
	proto.RegisterType((*Response)(nil), "protobuf.Response")
	proto.RegisterType((*CountResponse)(nil), "protobuf.CountResponse")
	proto.RegisterType((*ShowKeysResponse)(nil), "protobuf.ShowKeysResponse")
	proto.RegisterType((*ShowDataResponse)(nil), "protobuf.ShowDataResponse")
	proto.RegisterType((*ShowNamespacesResponse)(nil), "protobuf.ShowNamespacesResponse")
	proto.RegisterType((*NamespaceResponse)(nil), "protobuf.NamespaceResponse")
}

// Reference imports to suppress errors if they are not otherwise used.
var _ context.Context
var _ grpc.ClientConn

// This is a compile-time assertion to ensure that this generated file
// is compatible with the grpc package it is being compiled against.
const _ = grpc.SupportPackageIsVersion4

// Client API for KVS service

type KVSClient interface {
	// Inserts a key-value pair into a namespace, if not present
	Set(ctx context.Context, in *KeyValuePair, opts ...grpc.CallOption) (*Response, error)
	// Updates a key-value pair in a namespace, if present
	Update(ctx context.Context, in *KeyValuePair, opts ...grpc.CallOption) (*Response, error)
	// Checks if a key is in a namespace
	Has(ctx context.Context, in *Key, opts ...grpc.CallOption) (*Response, error)
	// Removes a key in a namespace, if present
	Unset(ctx context.Context, in *Key, opts ...grpc.CallOption) (*KeyValuePair, error)
	// Retrieves an element from a namespace under given key
	Get(ctx context.Context, in *Key, opts ...grpc.CallOption) (*KeyValuePair, error)
	// Returns the total number of key-value pairs in a namespace
	Count(ctx context.Context, in *google_protobuf.Empty, opts ...grpc.CallOption) (*CountResponse, error)
	// Retrieve all keys in a namespace
	ShowKeys(ctx context.Context, in *google_protobuf.Empty, opts ...grpc.CallOption) (*ShowKeysResponse, error)
	// Retrieve all key-value pairs in a namespace
	ShowData(ctx context.Context, in *google_protobuf.Empty, opts ...grpc.CallOption) (*ShowDataResponse, error)
	// Retrieve all namespaces in the key-value store that belongs to the user
	// NOTE: No token needed
	ShowNamespaces(ctx context.Context, in *google_protobuf.Empty, opts ...grpc.CallOption) (*ShowNamespacesResponse, error)
	// Changes the current namespace, returns a token that must be used for
	// subsequent requests
	// NOTE: No token needed
	UseNamespace(ctx context.Context, in *Namespace, opts ...grpc.CallOption) (*NamespaceResponse, error)
}

type kVSClient struct {
	cc *grpc.ClientConn
}

func NewKVSClient(cc *grpc.ClientConn) KVSClient {
	return &kVSClient{cc}
}

func (c *kVSClient) Set(ctx context.Context, in *KeyValuePair, opts ...grpc.CallOption) (*Response, error) {
	out := new(Response)
	err := grpc.Invoke(ctx, "/protobuf.KVS/Set", in, out, c.cc, opts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

func (c *kVSClient) Update(ctx context.Context, in *KeyValuePair, opts ...grpc.CallOption) (*Response, error) {
	out := new(Response)
	err := grpc.Invoke(ctx, "/protobuf.KVS/Update", in, out, c.cc, opts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

func (c *kVSClient) Has(ctx context.Context, in *Key, opts ...grpc.CallOption) (*Response, error) {
	out := new(Response)
	err := grpc.Invoke(ctx, "/protobuf.KVS/Has", in, out, c.cc, opts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

func (c *kVSClient) Unset(ctx context.Context, in *Key, opts ...grpc.CallOption) (*KeyValuePair, error) {
	out := new(KeyValuePair)
	err := grpc.Invoke(ctx, "/protobuf.KVS/Unset", in, out, c.cc, opts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

func (c *kVSClient) Get(ctx context.Context, in *Key, opts ...grpc.CallOption) (*KeyValuePair, error) {
	out := new(KeyValuePair)
	err := grpc.Invoke(ctx, "/protobuf.KVS/Get", in, out, c.cc, opts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

func (c *kVSClient) Count(ctx context.Context, in *google_protobuf.Empty, opts ...grpc.CallOption) (*CountResponse, error) {
	out := new(CountResponse)
	err := grpc.Invoke(ctx, "/protobuf.KVS/Count", in, out, c.cc, opts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

func (c *kVSClient) ShowKeys(ctx context.Context, in *google_protobuf.Empty, opts ...grpc.CallOption) (*ShowKeysResponse, error) {
	out := new(ShowKeysResponse)
	err := grpc.Invoke(ctx, "/protobuf.KVS/ShowKeys", in, out, c.cc, opts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

func (c *kVSClient) ShowData(ctx context.Context, in *google_protobuf.Empty, opts ...grpc.CallOption) (*ShowDataResponse, error) {
	out := new(ShowDataResponse)
	err := grpc.Invoke(ctx, "/protobuf.KVS/ShowData", in, out, c.cc, opts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

func (c *kVSClient) ShowNamespaces(ctx context.Context, in *google_protobuf.Empty, opts ...grpc.CallOption) (*ShowNamespacesResponse, error) {
	out := new(ShowNamespacesResponse)
	err := grpc.Invoke(ctx, "/protobuf.KVS/ShowNamespaces", in, out, c.cc, opts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

func (c *kVSClient) UseNamespace(ctx context.Context, in *Namespace, opts ...grpc.CallOption) (*NamespaceResponse, error) {
	out := new(NamespaceResponse)
	err := grpc.Invoke(ctx, "/protobuf.KVS/UseNamespace", in, out, c.cc, opts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

// Server API for KVS service

type KVSServer interface {
	// Inserts a key-value pair into a namespace, if not present
	Set(context.Context, *KeyValuePair) (*Response, error)
	// Updates a key-value pair in a namespace, if present
	Update(context.Context, *KeyValuePair) (*Response, error)
	// Checks if a key is in a namespace
	Has(context.Context, *Key) (*Response, error)
	// Removes a key in a namespace, if present
	Unset(context.Context, *Key) (*KeyValuePair, error)
	// Retrieves an element from a namespace under given key
	Get(context.Context, *Key) (*KeyValuePair, error)
	// Returns the total number of key-value pairs in a namespace
	Count(context.Context, *google_protobuf.Empty) (*CountResponse, error)
	// Retrieve all keys in a namespace
	ShowKeys(context.Context, *google_protobuf.Empty) (*ShowKeysResponse, error)
	// Retrieve all key-value pairs in a namespace
	ShowData(context.Context, *google_protobuf.Empty) (*ShowDataResponse, error)
	// Retrieve all namespaces in the key-value store that belongs to the user
	// NOTE: No token needed
	ShowNamespaces(context.Context, *google_protobuf.Empty) (*ShowNamespacesResponse, error)
	// Changes the current namespace, returns a token that must be used for
	// subsequent requests
	// NOTE: No token needed
	UseNamespace(context.Context, *Namespace) (*NamespaceResponse, error)
}

func RegisterKVSServer(s *grpc.Server, srv KVSServer) {
	s.RegisterService(&_KVS_serviceDesc, srv)
}

func _KVS_Set_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(KeyValuePair)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(KVSServer).Set(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: "/protobuf.KVS/Set",
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(KVSServer).Set(ctx, req.(*KeyValuePair))
	}
	return interceptor(ctx, in, info, handler)
}

func _KVS_Update_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(KeyValuePair)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(KVSServer).Update(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: "/protobuf.KVS/Update",
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(KVSServer).Update(ctx, req.(*KeyValuePair))
	}
	return interceptor(ctx, in, info, handler)
}

func _KVS_Has_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(Key)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(KVSServer).Has(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: "/protobuf.KVS/Has",
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(KVSServer).Has(ctx, req.(*Key))
	}
	return interceptor(ctx, in, info, handler)
}

func _KVS_Unset_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(Key)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(KVSServer).Unset(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: "/protobuf.KVS/Unset",
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(KVSServer).Unset(ctx, req.(*Key))
	}
	return interceptor(ctx, in, info, handler)
}

func _KVS_Get_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(Key)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(KVSServer).Get(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: "/protobuf.KVS/Get",
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(KVSServer).Get(ctx, req.(*Key))
	}
	return interceptor(ctx, in, info, handler)
}

func _KVS_Count_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(google_protobuf.Empty)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(KVSServer).Count(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: "/protobuf.KVS/Count",
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(KVSServer).Count(ctx, req.(*google_protobuf.Empty))
	}
	return interceptor(ctx, in, info, handler)
}

func _KVS_ShowKeys_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(google_protobuf.Empty)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(KVSServer).ShowKeys(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: "/protobuf.KVS/ShowKeys",
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(KVSServer).ShowKeys(ctx, req.(*google_protobuf.Empty))
	}
	return interceptor(ctx, in, info, handler)
}

func _KVS_ShowData_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(google_protobuf.Empty)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(KVSServer).ShowData(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: "/protobuf.KVS/ShowData",
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(KVSServer).ShowData(ctx, req.(*google_protobuf.Empty))
	}
	return interceptor(ctx, in, info, handler)
}

func _KVS_ShowNamespaces_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(google_protobuf.Empty)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(KVSServer).ShowNamespaces(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: "/protobuf.KVS/ShowNamespaces",
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(KVSServer).ShowNamespaces(ctx, req.(*google_protobuf.Empty))
	}
	return interceptor(ctx, in, info, handler)
}

func _KVS_UseNamespace_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(Namespace)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(KVSServer).UseNamespace(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: "/protobuf.KVS/UseNamespace",
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(KVSServer).UseNamespace(ctx, req.(*Namespace))
	}
	return interceptor(ctx, in, info, handler)
}

var _KVS_serviceDesc = grpc.ServiceDesc{
	ServiceName: "protobuf.KVS",
	HandlerType: (*KVSServer)(nil),
	Methods: []grpc.MethodDesc{
		{
			MethodName: "Set",
			Handler:    _KVS_Set_Handler,
		},
		{
			MethodName: "Update",
			Handler:    _KVS_Update_Handler,
		},
		{
			MethodName: "Has",
			Handler:    _KVS_Has_Handler,
		},
		{
			MethodName: "Unset",
			Handler:    _KVS_Unset_Handler,
		},
		{
			MethodName: "Get",
			Handler:    _KVS_Get_Handler,
		},
		{
			MethodName: "Count",
			Handler:    _KVS_Count_Handler,
		},
		{
			MethodName: "ShowKeys",
			Handler:    _KVS_ShowKeys_Handler,
		},
		{
			MethodName: "ShowData",
			Handler:    _KVS_ShowData_Handler,
		},
		{
			MethodName: "ShowNamespaces",
			Handler:    _KVS_ShowNamespaces_Handler,
		},
		{
			MethodName: "UseNamespace",
			Handler:    _KVS_UseNamespace_Handler,
		},
	},
	Streams:  []grpc.StreamDesc{},
	Metadata: "kvs.proto",
}

func init() { proto.RegisterFile("kvs.proto", fileDescriptor0) }

var fileDescriptor0 = []byte{
	// 430 bytes of a gzipped FileDescriptorProto
	0x1f, 0x8b, 0x08, 0x00, 0x00, 0x00, 0x00, 0x00, 0x02, 0xff, 0x94, 0x93, 0x5f, 0x6b, 0xd4, 0x40,
	0x14, 0xc5, 0x53, 0xb3, 0xa9, 0xc9, 0xb5, 0x95, 0x7a, 0x2d, 0xdb, 0x25, 0x15, 0x29, 0x03, 0x4a,
	0xdb, 0x87, 0x54, 0x5a, 0x28, 0xd2, 0x07, 0x11, 0xff, 0xa0, 0x10, 0x11, 0xc9, 0xb2, 0x7d, 0x9f,
	0xa6, 0xd7, 0x55, 0xb2, 0x9b, 0x09, 0x3b, 0x93, 0x95, 0x7c, 0x4d, 0x3f, 0x91, 0x64, 0xb2, 0xc9,
	0x24, 0xbb, 0x06, 0xba, 0x4f, 0x99, 0x7b, 0xe6, 0x77, 0xcf, 0xcd, 0x4c, 0x4e, 0xc0, 0x4b, 0x96,
	0x32, 0xc8, 0x16, 0x42, 0x09, 0x74, 0xf5, 0xe3, 0x2e, 0xff, 0xe9, 0x1f, 0x4f, 0x85, 0x98, 0xce,
	0xe8, 0xa2, 0x16, 0x2e, 0x68, 0x9e, 0xa9, 0xa2, 0xc2, 0xd8, 0x35, 0xec, 0x85, 0x54, 0xdc, 0xf2,
	0x59, 0x4e, 0x3f, 0xf8, 0xef, 0x05, 0x1e, 0x80, 0x9d, 0x50, 0x31, 0xda, 0x39, 0xd9, 0x39, 0xf5,
	0xa2, 0x72, 0x89, 0x87, 0xe0, 0x2c, 0xcb, 0xed, 0xd1, 0x23, 0xad, 0x55, 0x05, 0x3b, 0x02, 0x3b,
	0xa4, 0x62, 0x13, 0x67, 0x67, 0xe0, 0x7d, 0xe7, 0x73, 0x92, 0x19, 0x8f, 0x09, 0x5f, 0x80, 0x97,
	0xd6, 0xc5, 0x0a, 0x32, 0x02, 0xbb, 0x01, 0x37, 0x22, 0x99, 0x89, 0x54, 0x12, 0x8e, 0xe0, 0xb1,
	0xcc, 0xe3, 0x98, 0xa4, 0xd4, 0x9c, 0x1b, 0xd5, 0x65, 0xcf, 0xfc, 0x57, 0xb0, 0xff, 0x51, 0xe4,
	0xa9, 0x6a, 0x0c, 0x0e, 0xc1, 0x89, 0x4b, 0x41, 0xb7, 0x3b, 0x51, 0x55, 0xb0, 0xd7, 0x70, 0x30,
	0xfe, 0x25, 0xfe, 0x84, 0x54, 0xc8, 0x86, 0x44, 0x18, 0x24, 0x54, 0x94, 0x73, 0xec, 0x53, 0x2f,
	0xd2, 0x6b, 0xf6, 0xae, 0xe2, 0x3e, 0x71, 0xc5, 0x1b, 0xee, 0x1c, 0x06, 0xf7, 0x5c, 0x71, 0xcd,
	0x3d, 0xb9, 0x1c, 0x06, 0xf5, 0xfd, 0x05, 0xed, 0x0b, 0x8b, 0x34, 0xc3, 0xde, 0xc2, 0xb0, 0xec,
	0x6f, 0x4e, 0x6e, 0xa6, 0xbd, 0x04, 0x68, 0x4e, 0x5c, 0xcf, 0x6c, 0x29, 0xec, 0x0c, 0x9e, 0x35,
	0x5d, 0xed, 0xc3, 0x28, 0x91, 0x50, 0xba, 0xba, 0xb3, 0xaa, 0xb8, 0xfc, 0x3b, 0x00, 0x3b, 0xbc,
	0x1d, 0xe3, 0x15, 0xd8, 0x63, 0x52, 0xd8, 0xf3, 0x46, 0x3e, 0x1a, 0xbd, 0x36, 0x64, 0x16, 0x5e,
	0xc3, 0xee, 0x24, 0xbb, 0xe7, 0x8a, 0xb6, 0xec, 0x3b, 0x07, 0xfb, 0x2b, 0x97, 0xb8, 0xdf, 0x69,
	0xea, 0x61, 0xdf, 0x80, 0x33, 0x49, 0x25, 0xa9, 0x75, 0xba, 0x67, 0x22, 0xb3, 0x30, 0x00, 0xfb,
	0xcb, 0x36, 0xfc, 0x0d, 0x38, 0xfa, 0xb3, 0xe3, 0x30, 0xa8, 0x52, 0x6d, 0xc8, 0xcf, 0x65, 0xaa,
	0xfd, 0x23, 0x23, 0x74, 0xf2, 0xc1, 0x2c, 0x7c, 0x0f, 0x6e, 0x9d, 0x85, 0xde, 0x76, 0xdf, 0x08,
	0xeb, 0xb9, 0x31, 0x0e, 0x65, 0x4a, 0x1e, 0xea, 0xd0, 0x4e, 0x14, 0xb3, 0xf0, 0x1b, 0x3c, 0xed,
	0xe6, 0xa4, 0xd7, 0xe7, 0xa4, 0xeb, 0xb3, 0x99, 0x2c, 0x66, 0xe1, 0x07, 0xd8, 0x9b, 0x48, 0x32,
	0xbf, 0xdb, 0x73, 0xd3, 0xd3, 0x88, 0xfe, 0xf1, 0x7f, 0x44, 0xe3, 0x71, 0xb7, 0xab, 0x77, 0xaf,
	0xfe, 0x05, 0x00, 0x00, 0xff, 0xff, 0x67, 0x8b, 0x55, 0x5b, 0x3b, 0x04, 0x00, 0x00,
}
