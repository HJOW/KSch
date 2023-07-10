# KSch
Fork of JSch (by JCraft Inc.) project for customizing filename character set.
Almost every classes are just same as JSch project excepts...

ChannelSftp
KChannelSftp (New)
Channel

All package names are changed for prevent conflict with JSch.

이 프로젝트는 JSch 의 포크입니다.
파일 이름 캐릭터셋 문제로 SSH 접속이 불가능한 경우를 위해 따로 만든 프로젝트입니다.
원본 프로젝트는 BSD-style 라이센스가 적용되어 있으며, 이 프로젝트 또한 동일 규격을 따릅니다.

JSch : https://github.com/is/jsch/

대다수의 클래스들은 그대로 사용 (단, 원래 클래스와 충돌방지를 위해 패키지명은 변경)
ChannelSftp 클래스의 대체재로 KChannelSftp 클래스가 추가되었습니다.
sftp 채널 생성 시 이 대체 클래스로 객체가 생성됩니다.
setFilenameEncoding(String) 메소드가 오버라이드되어 다른 캐릭터셋을 받을 수 있게 되었습니다.
다음 클래스를 제외하면 모두 기존 JSch 와 동일합니다.

ChannelSftp
KChannelSftp
Channel