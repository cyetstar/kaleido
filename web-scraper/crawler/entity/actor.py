from dataclasses import dataclass, field


@dataclass
class Actor:
    douban_id: str = None
    thumb: str = None
    role: str = None
    cn_name: str = None
    en_name: str = None
    actor_type: str = None
